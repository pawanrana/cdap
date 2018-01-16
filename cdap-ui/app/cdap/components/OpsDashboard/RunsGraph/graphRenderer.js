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

import d3 from 'd3';

export function renderGraph(selector, containerWidth, containerHeight, data) {
  let margin = {
    top: 20,
    right: 30,
    bottom: 30,
    left: 40
  };
  let width = containerWidth - margin.left - margin.right,
      height = containerHeight - margin.top - margin.bottom;

  let svg = d3.select(selector)
    .attr('height', containerHeight)
    .attr('width', containerWidth);

  let x = d3.scaleOrdinal()
    .rangeRound([0, width])
    .paddingInner(0.05)
    .align(0.1);

  let y = d3.scaleLinear()
    .rangeRound([height, 0]);

  let colorScale = d3.scaleOrdinal()
    .range([
      '#979fbb',
      '#454A57',
      '#0076dc',
      '#3cc801',
      '#d40001'
    ]);


  // SETTING DOMAINS
  x.domain(data.map((d) => d.time));
  y.domain([0, d3.max(data, (d) => d.manual + d.schedule)]);
  colorScale.domain([
    'manual',
    'schedule',
    'running',
    'successful',
    'failed'
  ]);


  // RENDER AXIS

  // X Axis
  svg.append('g')
    .attr('class', 'axis')
    .attr('transform', `translate(0, ${height})`)
    .call(d3.axisBottom(x));

  // // Y Axis
  // svg.append('g')
  //   .attr('class', 'axis')
  //   .attr()

}
