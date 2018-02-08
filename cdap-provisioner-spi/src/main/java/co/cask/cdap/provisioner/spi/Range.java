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

/**
 * A number range.
 *
 * @param <T> type of number range
 */
public class Range<T extends Number> {
  private final T min;
  private final T max;
  private final boolean minInclusive;
  private final boolean maxInclusive;

  public Range(T min, T max) {
    this(min, max, true, false);
  }

  public Range(T min, T max, boolean minInclusive, boolean maxInclusive) {
    this.min = min;
    this.max = max;
    this.minInclusive = minInclusive;
    this.maxInclusive = maxInclusive;
  }

  public T getMin() {
    return min;
  }

  public T getMax() {
    return max;
  }

  public boolean isMinInclusive() {
    return minInclusive;
  }

  public boolean isMaxInclusive() {
    return maxInclusive;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Range<?> that = (Range<?>) o;

    return Objects.equals(min, that.min) &&
      Objects.equals(max, that.max) &&
      minInclusive == that.minInclusive &&
      maxInclusive == that.maxInclusive;
  }

  @Override
  public int hashCode() {
    return Objects.hash(min, max, minInclusive, maxInclusive);
  }

  @Override
  public String toString() {
    return (minInclusive ? "[" : "(") + min + "," + max + (maxInclusive ? "]" : ")");
  }
}
