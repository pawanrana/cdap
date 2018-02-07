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

package co.cask.cdap.provisioner;

import co.cask.cdap.provisioner.spi.Cluster;
import co.cask.cdap.provisioner.spi.OperationStatus;
import co.cask.cdap.provisioner.spi.Provisioner;
import co.cask.cdap.provisioner.spi.ProvisionerContext;
import co.cask.cdap.provisioner.spi.ProvisionerSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Default provisioner that doesn't provision a cluster
 */
public class DefaultProvisioner implements Provisioner {
  private static final ProvisionerSpec SPEC = new ProvisionerSpec(
    "default", "Default Provisioner",
    "Runs programs on the CDAP master cluster. Does not provision any resources.",
    new HashMap<>());

  @Override
  public ProvisionerSpec getSpec() {
    return SPEC;
  }

  @Override
  public void validateProperties(Map<String, String> properties) {
    // no-op
  }

  @Override
  public Cluster requestCreate(ProvisionerContext context) {
    return new Cluster(context.getProgramRun().getRun(), new ArrayList<>(), new HashMap<>());
  }

  @Override
  public OperationStatus getCreateStatus(ProvisionerContext context, Cluster cluster) {
    return OperationStatus.SUCCESSFUL;
  }

  @Override
  public void requestDelete(ProvisionerContext context, Cluster cluster) {
    // no-op
  }

  @Override
  public OperationStatus getDeleteStatus(ProvisionerContext context, Cluster cluster) {
    return OperationStatus.SUCCESSFUL;
  }
}
