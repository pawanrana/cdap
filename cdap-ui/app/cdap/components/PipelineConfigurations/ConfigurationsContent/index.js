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

import React from 'react';
import PropTypes from 'prop-types';
import {TAB_OPTIONS} from 'components/PipelineConfigurations/Store';
import PipelineConfigTabContent from 'components/PipelineConfigurations/ConfigurationsContent/PipelineConfigTabContent';
import EngineConfig from 'components/PipelineConfigurations/ConfigurationsContent/EngineConfig';
import ResourcesConfig from 'components/PipelineConfigurations/ConfigurationsContent/ResourcesConfig';
import classnames from 'classnames';
require('./ConfigurationsContent.scss');

export default function ConfigurationsContent({isBatch, activeTab, isDetailView}) {
  let ContentToShow;
  switch (activeTab) {
    case TAB_OPTIONS.PIPELINE_CONFIG:
      ContentToShow = PipelineConfigTabContent;
      break;
    case TAB_OPTIONS.ENGINE_CONFIG:
      ContentToShow = EngineConfig;
      break;
    case TAB_OPTIONS.RESOURCES:
      ContentToShow = ResourcesConfig;
      break;
    default:
      // Other tabs will just be empty for now
      ContentToShow = 'div';
  }
  return (
    <div
      className={classnames("configuration-content", {
        "configuration-content-batch": isBatch,
        "configuration-content-realtime": !isBatch
      })}
    >
      <ContentToShow
        isBatch={isBatch}
        isDetailView={isDetailView}
      />
    </div>
  );
}

ConfigurationsContent.propTypes = {
  isBatch: PropTypes.bool,
  activeTab: PropTypes.string,
  isDetailView: PropTypes.bool
};
