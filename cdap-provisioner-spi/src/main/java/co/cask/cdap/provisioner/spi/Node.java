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

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Information about a cluster node.
 */
public class Node {
  private final String id;
  private final long createtime;
  private final Map<String, String> properties;

  public Node(String id, long createtime, Map<String, String> properties) {
    this.id = id;
    this.createtime = createtime;
    this.properties = Collections.unmodifiableMap(properties);
  }

  public String getId() {
    return id;
  }

  public long getCreatetime() {
    return createtime;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Node that = (Node) o;

    return Objects.equals(id, that.id) && createtime == that.createtime && Objects.equals(properties, that.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createtime, properties);
  }

  @Override
  public String toString() {
    return "Node{" +
      "id='" + id + '\'' +
      ", createtime=" + createtime +
      ", properties=" + properties +
      '}';
  }
}
