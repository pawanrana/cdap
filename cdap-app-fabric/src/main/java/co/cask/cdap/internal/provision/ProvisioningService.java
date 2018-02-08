/*
 * Copyright © 2018 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package co.cask.cdap.internal.provision;

import co.cask.cdap.common.NotFoundException;
import co.cask.cdap.common.conf.CConfiguration;
import co.cask.cdap.common.conf.Constants;
import co.cask.cdap.provisioner.spi.Provisioner;
import co.cask.cdap.provisioner.spi.ProvisionerSpec;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/**
 * Service for provisioning related operations
 */
public class ProvisioningService extends AbstractIdleService {
  private static final Logger LOG = LoggerFactory.getLogger(ProvisioningService.class);
  private final ProvisionerExtensionLoader provisionerExtensionLoader;
  private final AtomicReference<ProvisionerInfo> provisionerInfo;

  @Inject
  ProvisioningService(CConfiguration cConf) {
    provisionerExtensionLoader = new ProvisionerExtensionLoader(cConf.get(Constants.Provisioner.EXTENSIONS_DIR));
    provisionerInfo = new AtomicReference<>(new ProvisionerInfo(new HashMap<>(), new HashMap<>()));
  }

  @Override
  protected void startUp() throws Exception {
    reloadProvisioners();
  }

  @Override
  protected void shutDown() throws Exception {
    // no-op
  }

  /**
   * Reloads provisioners in the extension directory. Any new provisioners will be added and any deleted provisioners
   * will be removed.
   */
  public void reloadProvisioners() {
    Map<String, Provisioner> provisioners = provisionerExtensionLoader.getAll();
    Map<String, ProvisionerSpec> specs = new HashMap<>(provisioners.size());
    for (Map.Entry<String, Provisioner> provisionerEntry : provisioners.entrySet()) {
      LOG.debug("Registering {} provisioner", provisionerEntry.getKey());
      specs.put(provisionerEntry.getKey(), provisionerEntry.getValue().getSpec());
    }
    provisionerInfo.set(new ProvisionerInfo(provisioners, specs));
  }

  /**
   * @return unmodifiable collection of all provisioner specs
   */
  public Collection<ProvisionerSpec> getProvisionerSpecs() {
    return provisionerInfo.get().specs.values();
  }

  /**
   * Get the spec for the specified provisioner.
   *
   * @param name the name of the provisioner
   * @return the spec for the provisioner, or null if the provisioner does not exist
   */
  @Nullable
  public ProvisionerSpec getProvisionerSpec(String name) {
    return provisionerInfo.get().specs.get(name);
  }

  /**
   * Validate properties for the specified provisioner.
   *
   * @param provisionerName the name of the provisioner to validate
   * @param properties properties for the specified provisioner
   * @throws NotFoundException if the provisioner does not exist
   * @throws IllegalArgumentException if the properties are invalid
   */
  public void validateProperties(String provisionerName, Map<String, String> properties) throws NotFoundException {
    Provisioner provisioner = provisionerInfo.get().provisioners.get(provisionerName);
    if (provisioner == null) {
      throw new NotFoundException(String.format("Provisioner '%s' does not exist", provisionerName));
    }
    provisioner.validateProperties(properties);
  }

  /**
   * Just a container for provisioner instances and specs, so that they can be updated atomically.
   */
  private static class ProvisionerInfo {
    private final Map<String, Provisioner> provisioners;
    private final Map<String, ProvisionerSpec> specs;

    private ProvisionerInfo(Map<String, Provisioner> provisioners, Map<String, ProvisionerSpec> specs) {
      this.provisioners = Collections.unmodifiableMap(provisioners);
      this.specs = Collections.unmodifiableMap(specs);
    }
  }
}
