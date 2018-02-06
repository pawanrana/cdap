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
package co.cask.cdap.api.metadata;

/**
 * This interface provides methods that to create {@link MetadataEntity} for various resources present in CDAP.
 * Additionally, this interface also includes methods to add metadata to various {@link MetadataEntity}.
 */
public interface MetadataContext {

  /**
   * Returns a metadata entity for the given dataset name present in the current namespace.
   *
   * @param datasetName the name of the dataset
   * @return {@link MetadataEntity} for the given dataset in the current namespace
   */
  MetadataEntity getDatasetEntity(String datasetName);

  /**
   * Returns the metadata entity for the given dataset name present in the given namespace
   *
   * @param namespace the namespace for the dataset
   * @param datasetName the dataset name
   * @return {@link MetadataEntity} for the given dataset in the given namespace
   */
  MetadataEntity getDatasetEntity(String namespace, String datasetName);

  /**
   * Returns the metadata entity for the given steam name in the current namespace
   *
   * @param streamName the name of the stream
   * @return {@link MetadataEntity} for the given stream in the current namespace
   */
  MetadataEntity getStreamEntity(String streamName);

  /**
   * Returns the metadata entity for the given stream in the given namespace
   *
   * @param namespace the namespace for the stream
   * @param streamName the stream name
   * @return {@link MetadataEntity} for the given stream in the given namespace
   */
  MetadataEntity getStreamEntity(String namespace, String streamName);

  /**
   * Returns the metadata entity for the given field of the dataset present in the current namespace
   *
   * @param datasetName the name of the dataset
   * @param fieldName the fieldname
   * @return {@link MetadataEntity} for the given field of the dataset in the current namespace
   */
  MetadataEntity getSchemaFieldEntity(String datasetName, String fieldName);

  /**
   * Returns the metadata entity for the given field of the dataset present in the given namespace
   *
   * @param namespace the namespace for the dataset
   * @param datasetName the name of the dataset
   * @param fieldName the fieldname
   * @return {@link MetadataEntity} for the given field of the dataset in the given namespace
   */
  MetadataEntity getSchemaFieldEntity(String namespace, String datasetName, String fieldName);

  //TODO: Add more methods here to support adding, retrieving and deleting metadata
}
