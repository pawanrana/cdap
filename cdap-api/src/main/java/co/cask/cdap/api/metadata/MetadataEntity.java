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

import co.cask.cdap.api.dataset.lib.KeyValue;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Entity representation for Metadata
 */
public class MetadataEntity {

  public static final String NAMESPACE = "namespace";
  public static final String ARTIFACT = "artifact";
  public static final String APPLICATION = "application";
  public static final String DATASET = "dataset";
  public static final String STREAM = "stream";
  public static final String PROGRAM_TYPE = "program_type";
  public static final String PROGRAM_NAME = "program_name";
  public static final String STREAM_VIEW = "view";
  public static final String VERSION = "version";
  public static final String FIELD = "field";


  private List<KeyValue<String, String>> details;
  private boolean customEntity;

  private MetadataEntity(List<KeyValue<String, String>> details) {
    this.details = details;
    this.customEntity = false;
  }

  private MetadataEntity(List<KeyValue<String, String>> details, boolean customEntity) {
    this.details = details;
    this.customEntity = customEntity;
  }

  /**
   * @return A {@link List} of {@link KeyValue} of representing the the metadata entity
   */
  public List<KeyValue<String, String>> getDetails() {
    return details;
  }

  /**
   * @return the type of the metadata entity
   */
  public String getType() {
    // TODO: Consider whether we should we store the type explicitly rather than determining it from the last part;
    // should the user be able to specify the type by themselves
    return details.get(details.size() - 1).getKey();
  }

  /**
   * @return true if the metadata entity is custom entity else false. See
   * {@link MetadataEntity.Builder#setCustomEntity(boolean)} for details on custom entity.
   */
  public boolean isCustomEntity() {
    return customEntity;
  }

  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder for {@link MetadataEntity}
   */
  public static class Builder {
    private final List<KeyValue<String, String>> details = new LinkedList<>();
    private boolean customEntity;

    /**
     * Adds the given key which is resource identifier with the given value specifying the name of the resource
     *
     * @param k the resource identifier
     * @param v the name of the resource
     * @return {@link MetadataEntity.Builder} which has the given k and v as part of builder
     */
    public Builder add(String k, String v) {
      details.add(new KeyValue<>(k, v));
      return this;
    }

    /**
     * Sets whether the Metadata Entity is a custom entity or not.
     * Custom Entity are Metadata Entity representing resources in CDAP which are not EntityIds.
     *
     * @param customEntity true to specify that this is a custom entity else false
     * @return {@link MetadataEntity.Builder} which specifies whether the Metadata Entity is custom entity or not
     */
    public Builder setCustomEntity(boolean customEntity) {
      this.customEntity = customEntity;
      return this;
    }

    /**
     * Builds a metadata entity for Dataset which represents Dataset.
     *
     * @param namespace the name of the namespace
     * @param datasetName the name of the dataset
     * @return {@link MetadataEntity.Builder} which represents the specified dataset
     */
    public Builder forDataset(String namespace, String datasetName) {
      details.add(new KeyValue<>(NAMESPACE, namespace));
      details.add(new KeyValue<>(DATASET, datasetName));
      return this;
    }

    /**
     * Builds a metadata entity for Stream which represents StreamId.
     *
     * @param namespace the name of the namespace
     * @param streamName the name of the stream
     * @return {@link MetadataEntity.Builder} which represents the specified stream
     */
    public Builder forStream(String namespace, String streamName) {
      details.add(new KeyValue<>(NAMESPACE, namespace));
      details.add(new KeyValue<>(STREAM, streamName));
      return this;
    }

    /**
     * Builds a Metadata Entity for Application which represents ApplicationId with a version.
     *
     * @param namespace the name of the namespace
     * @param applicationName the name of the application
     * @param version the version of the application
     * @return {@link MetadataEntity.Builder} which represents the specified application with version
     */
    public Builder forApplication(String namespace, String applicationName, String version) {
      details.add(new KeyValue<>(NAMESPACE, namespace));
      details.add(new KeyValue<>(APPLICATION, applicationName));
      details.add(new KeyValue<>(VERSION, version));
      return this;
    }

    /**
     * Builds a Metadata Entity for Application which represents ApplicationId with a version.
     *
     * @param namespace the name of the namespace
     * @param artifactName the name of the artifact
     * @param version the version of the artifact
     * @return {@link MetadataEntity.Builder} which represents the specified artifact with version
     */
    public Builder forArtifact(String namespace, String artifactName, String version) {
      details.add(new KeyValue<>(NAMESPACE, namespace));
      details.add(new KeyValue<>(ARTIFACT, artifactName));
      details.add(new KeyValue<>(VERSION, version));
      return this;
    }

    /**
     * Builds a metadata entity for Program which represents ProgramId
     *
     * @param namespace the name of the namespace
     * @param applicationName the name of the application
     * @param programType the type of the program
     * @param programName the name of the program
     * @return {@link MetadataEntity.Builder} which represents the specified program
     */
    public Builder forProgram(String namespace, String applicationName, String programType, String programName) {
      details.add(new KeyValue<>(NAMESPACE, namespace));
      details.add(new KeyValue<>(APPLICATION, applicationName));
      details.add(new KeyValue<>(PROGRAM_TYPE, programType));
      details.add(new KeyValue<>(PROGRAM_NAME, programName));
      return this;
    }

    /**
     * Builds a metadata entity for Stream view which represents ViewId.
     *
     * @param namespace the name of the namespace
     * @param streamName the name of the stream
     * @param viewName the name of the view
     * @return {@link MetadataEntity.Builder} which represents the specified view
     */
    public Builder forView(String namespace, String streamName, String viewName) {
      details.add(new KeyValue<>(NAMESPACE, namespace));
      details.add(new KeyValue<>(STREAM, streamName));
      details.add(new KeyValue<>(STREAM_VIEW, viewName));
      return this;
    }

    /**
     * Builds the {@link MetadataEntity} from the {@link MetadataEntity.Builder}. It is the caller's responsibility
     * to set whether the {@link MetadataEntity} is custom or not before building through
     * {@link Builder#setCustomEntity(boolean)} ()}
     *
     * @return {@link MetadataEntity} the metadata entity
     */
    public MetadataEntity build() {
      return new MetadataEntity(Collections.unmodifiableList(details), customEntity);
    }
  }
}
