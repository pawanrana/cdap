/*
 * Copyright © 2016 Cask Data, Inc.
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

import Header from '../Header';
import T from 'i18n-react';

export default function CdapHeader() {

  var navbarItemList = [
    {
      linkTo: '/ns',
      title: T.translate('features.Navbar.CDAP.home')
    },
    // FIXME: Add later.
    // {
    //   linkTo: '/dashboard',
    //   disabled: true,
    //   className: 'disabled',
    //   title: T.translate('features.Navbar.CDAP.dashboard')
    // },
    {
      linkTo: '/management',
      title: T.translate('features.Navbar.CDAP.management')
    }
  ];

  return (
    <Header
      navbarItemList={navbarItemList}
    />
  );
}