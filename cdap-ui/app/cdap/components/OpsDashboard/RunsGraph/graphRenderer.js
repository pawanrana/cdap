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

import * as d3 from 'd3';

export function renderGraph(selector, containerWidth, containerHeight, data) {
  let margin = {
    top: 20,
    right: 50,
    bottom: 30,
    left: 50
  };
  let width = containerWidth - margin.left - margin.right,
      height = containerHeight - margin.top - margin.bottom;

  let svg = d3.select(selector)
    .attr('height', containerHeight)
    .attr('width', containerWidth);

  let chart = svg.append('g')
    .attr('transform', `translate(${margin.left}, ${margin.top})`);

  let x = d3.scaleBand()
    .rangeRound([0, width])
    .paddingInner(0)
    .paddingOuter(0);

  let yLeft = d3.scaleLinear()
    .rangeRound([height, 0]);

  let yRight = d3.scaleLinear()
    .rangeRound([height, 0]);

  const colorMap = {
    manual: '#979fbb',
    schedule: '#454A57',
    running: '#0076dc',
    successful: '#3cc801',
    failed: '#d40001'
  };

  // SETTING DOMAINS
  x.domain(data.map((d) => d.time));
  yLeft.domain([0, d3.max(data, (d) => Math.max(d.manual + d.schedule, d.running + d.successful + d.failed) )]);
  yRight.domain([0, d3.max(data, (d) => d.delay)]);


  // RENDER AXIS
  let barPadding = 2;
  let barWidth = (x.bandwidth() - barPadding * 6) / 2;

  // X Axis
  let xAxis = d3.axisBottom(x)
    .tickSizeInner(-height)
    .tickSizeOuter(0);

  let axisOffset = barWidth + barPadding * 2;
  let xAxisGroup = chart.append('g')
    .attr('class', 'axis axis-x')
    .attr('transform', `translate(${axisOffset}, ${height})`)
    .call(xAxis);

  xAxisGroup.select('.domain')
    .remove();

  xAxisGroup.selectAll('text')
    .attr('x', -axisOffset);

  xAxisGroup.select('.tick:last-child')
    .select('line')
    .remove();


  // Y Axis Left
  chart.append('g')
    .attr('class', 'axis axis-y-left')
    .call(d3.axisLeft(yLeft));

  // Y Axis Right
  chart.append('g')
    .attr('class', 'axis axis-y-right')
    .attr('transform', `translate(${width}, 0)`)
    .call(d3.axisRight(yRight).tickSizeOuter(-width));


  // Render bar graph
  function getXLocation(d) {
    return x(d.time) + barPadding * 2;
  }


  // Start method
  let barStartMethod = chart.append('g')
    .attr('class', 'bar-start-method');

  let startMethod = barStartMethod.selectAll('rect')
    .data(data)
    .enter();

  // Schedule
  startMethod.append('rect')
    .attr('class', 'bar')
    .attr('fill', colorMap['schedule'])
    .attr('width', barWidth)
    .attr('x', getXLocation)
    .attr('y', (d) => yLeft(d.schedule))
    .attr('height', (d) => height - yLeft(d.schedule));

  // Manual
  startMethod.append('rect')
    .attr('class', 'bar')
    .attr('fill', colorMap['manual'])
    .attr('width', barWidth)
    .attr('x', getXLocation)
    .attr('y', (d) => yLeft(d.manual) - (height - yLeft(d.schedule)) )
    .attr('height', (d) => height - yLeft(d.manual));



  // Statistics
  let statisticsOffsetX = barWidth + barPadding;
  let barStatistics = chart.append('g')
    .attr('class', 'bar-statistics')
    .attr('transform', `translate(${statisticsOffsetX}, 0)`);


  let statistics = barStatistics.selectAll('rect')
    .data(data)
    .enter();

  // Running
  statistics.append('rect')
    .attr('class', 'bar')
    .attr('fill', colorMap['running'])
    .attr('width', barWidth)
    .attr('x', getXLocation)
    .attr('y', (d) => yLeft(d.running))
    .attr('height', (d) => height - yLeft(d.running));

  // Successful
  statistics.append('rect')
    .attr('class', 'bar')
    .attr('fill', colorMap['successful'])
    .attr('width', barWidth)
    .attr('x', getXLocation)
    .attr('y', (d) => yLeft(d.successful) - (height - yLeft(d.running)))
    .attr('height', (d) => height - yLeft(d.successful));

  // Failed
  statistics.append('rect')
    .attr('class', 'bar')
    .attr('fill', colorMap['failed'])
    .attr('width', barWidth)
    .attr('x', getXLocation)
    .attr('y', (d) => yLeft(d.failed) - (height - yLeft(d.running)) - (height - yLeft(d.successful)))
    .attr('height', (d) => height - yLeft(d.failed));



  // Render line graph
  let line = d3.line()
    .x((d) => x(d.time))
    .y((d) => yRight(d.delay));

  let offsetX = barWidth + barPadding * 2;

  let pathGroup = chart.append('g')
    .attr('class', 'line-graph')
    .attr('transform', `translate(${offsetX}, 0)`);

  pathGroup.append('path')
    .datum(data)
    .attr('fill', 'none')
    .attr('stroke', '#bbbbbb')
    .attr('stroke-linejoin', 'round')
    .attr('stroke-linecap', 'round')
    .attr('stroke-width', 1.5)
    .attr('d', line);

  pathGroup.append('g')
      .attr('class', 'dots')
    .selectAll('circle')
      .data(data)
    .enter().append('circle')
      .attr('class', 'dot')
      .attr('r', 5)
      .attr('cx', (d) => x(d.time))
      .attr('cy', (d) => yRight(d.delay))
      .style('fill', '#bbbbbb')
      .style('stroke', 'white')
      .style('stroke-width', 2);

}
