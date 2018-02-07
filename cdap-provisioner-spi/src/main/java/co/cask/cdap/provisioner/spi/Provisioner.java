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

package co.cask.cdap.provisioner.spi;

import java.util.Map;

/**
 * A Provisioner is responsible for creating and deleting clusters for program runs. Each method may be retried
 * in case of a failure, so must be implemented in an idempotent way.
 */
public interface Provisioner {

  /**
   * Return the specification for the provisioner.
   *
   * @return the provisioner specification
   */
  ProvisionerSpec getSpec();

  /**
   * Check that the specified properties are valid. If they are not, an IllegalArgumentException should be thrown.
   *
   * @param properties properties to validate
   * @throws IllegalArgumentException if the properties are invalid
   */
  void validateProperties(Map<String, String> properties);

  /**
   * Request to create a cluster. The cluster does not have to be operational before the method returns.
   * Must be implemented in an idempotent way, otherwise there may be resource leaks. This means the implementation
   * must first check if the cluster exists before trying to create it, otherwise multiple clusters may be created
   * for a single program run.
   *
   * @param context provisioner context
   * @return information about the cluster
   */
  Cluster requestCreate(ProvisionerContext context);

  /**
   * Get the status of the request to create the cluster.
   *
   * @param context provisioner context
   * @param cluster the cluster to get the status of
   * @return status of the create request
   */
  OperationStatus getCreateStatus(ProvisionerContext context, Cluster cluster);

  /**
   * Request to delete a cluster. The cluster does not have to be deleted before the method returns.
   * Must be implemented in an idempotent way.
   *
   * @param context provisioner context
   * @param cluster the cluster to delete
   */
  void requestDelete(ProvisionerContext context, Cluster cluster);

  /**
   * Get the status of the request to delete the cluster. Must be implemented in an idempotent way.
   *
   * @param context provisioner context
   * @param cluster the cluster to get the status of
   * @return status of the delete request
   */
  OperationStatus getDeleteStatus(ProvisionerContext context, Cluster cluster);
}
