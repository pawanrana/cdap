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

import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;

/**
 * Property of a Provisioner. Includes optional restrictions on the property value and range.
 */
public class ProvisionerProperty {
  private final String name;
  private final String label;
  private final String description;
  private final String type;
  private final Set<String> values;
  private final Range range;

  public ProvisionerProperty(String name, String label, String description, String type,
                             @Nullable Set<String> values, @Nullable Range range) {
    this.name = name;
    this.label = label;
    this.description = description;
    this.type = type;
    this.values = values;
    this.range = range;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ProvisionerProperty that = (ProvisionerProperty) o;

    return Objects.equals(name, that.name) &&
      Objects.equals(label, that.label) &&
      Objects.equals(description, that.description) &&
      Objects.equals(type, that.type) &&
      Objects.equals(values, that.values) &&
      Objects.equals(range, that.range);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, label, description, type, values, range);
  }
}
