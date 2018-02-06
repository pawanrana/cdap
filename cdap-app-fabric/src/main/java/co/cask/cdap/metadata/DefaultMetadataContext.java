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
package co.cask.cdap.metadata;

import co.cask.cdap.api.RuntimeContext;
import co.cask.cdap.api.metadata.MetadataContext;
import co.cask.cdap.api.metadata.MetadataEntity;

/**
 * Default implementation for {@link MetadataContext}
 */
public class DefaultMetadataContext implements MetadataContext {

  private final RuntimeContext runtimeContext;

  public DefaultMetadataContext(RuntimeContext runtimeContext) {
    this.runtimeContext = runtimeContext;
  }

  @Override
  public MetadataEntity getDatasetEntity(String datasetName) {
    return MetadataEntity.builder().forDataset(runtimeContext.getNamespace(), datasetName).build();
  }

  @Override
  public MetadataEntity getDatasetEntity(String namespace, String datasetName) {
    return MetadataEntity.builder().forDataset(namespace, datasetName).build();
  }

  @Override
  public MetadataEntity getStreamEntity(String streamName) {
    return MetadataEntity.builder().forDataset(runtimeContext.getNamespace(), streamName).build();
  }

  @Override
  public MetadataEntity getStreamEntity(String namespace, String streamName) {
    return MetadataEntity.builder().forDataset(namespace, streamName).build();
  }

  @Override
  public MetadataEntity getSchemaFieldEntity(String datasetName, String fieldName) {
    return MetadataEntity.builder().forDataset(runtimeContext.getNamespace(),
                                               datasetName).add(MetadataEntity.FIELD, fieldName).build();
  }

  @Override
  public MetadataEntity getSchemaFieldEntity(String namespace, String datasetName, String fieldName) {
    return MetadataEntity.builder().forDataset(namespace, datasetName).add(MetadataEntity.FIELD, fieldName).build();
  }
}
