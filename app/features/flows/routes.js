angular.module(PKG.name + '.feature.flows')
  .config(function($stateProvider, $urlRouterProvider, MYAUTH_ROLE) {
    $stateProvider
      .state('flows', {
        url: '/flows',
        abstract: true,
        parent: 'programs',
        data: {
          authorizedRoles: MYAUTH_ROLE.all,
          highlightTab: 'development'
        },
        template: '<ui-view/>'
      })
        .state('flows.list', {
          url: '/list',
          templateUrl: '/assets/features/flows/templates/list.html',
          controller: 'FlowsListController',
          ncyBreadcrumb: {
            skip: true
          }
        })
        .state('flows.detail', {
          url: '/:programId',
          templateUrl: '/assets/features/flows/templates/detail.html',
          controller: 'FlowsDetailController',
          onEnter: function($state, $timeout) {

            $timeout(function() {
              if ($state.is('flows.detail')) {
                $state.go('flows.detail.runs');
              }
            });

          },
          ncyBreadcrumb: {
            parent: 'apps.detail.overview',
            label: '{{$state.params.programId | caskCapitalizeFilter}}'
          }
        })
          .state('flows.detail.runs', {
            url: '',
            templateUrl: '/assets/features/flows/templates/tabs/runs.html',
            controller: 'FlowsDetailRunController',
            ncyBreadcrumb: {
              skip: true
            }
          })

            .state('flows.detail.runs.detail', {
              url: '/runs/:runId',
              template: '<ui-view/>',
              abstract: true
            })
              .state('flows.detail.runs.detail.status', {
                url: '/status',
                templateUrl: '/assets/features/flows/templates/tabs/runs/status.html',
                controller: 'FlowsDetailRunStatusController',
                ncyBreadcrumb: {
                  skip: true
                }
              })
              .state('flows.detail.runs.detail.flowlets', {
                url: '/flowlets',
                templateUrl: '/assets/features/flows/templates/tabs/runs/flowlets.html',
                ncyBreadcrumb: {
                  skip: true
                }
              })
                .state('flows.detail.runs.detail.flowlets.detail', {
                  url: '/:flowletId',
                  templateUrl: '/assets/features/flows/templates/tabs/runs/flowlets/detail.html',
                  controller: 'FlowsFlowletDetaiController',
                  ncyBreadcrumb: {
                    skip: true
                  }
                })
              .state('flows.detail.runs.detail.data', {
                url: '/data',
                templateUrl: '/assets/features/flows/templates/tabs/runs/data.html',
                ncyBreadcrumb: {
                  skip: true
                }
              })
              .state('flows.detail.runs.detail.configuration', {
                url: '/configuration',
                templateUrl: '/assets/features/flows/templates/tabs/runs/configuration.html',
                ncyBreadcrumb: {
                  skip: true
                }
              })
              .state('flows.detail.runs.detail.log', {
                url: '/logs?filter',
                reloadOnSearch: false,
                template: '<my-log-viewer data-model="logs"></my-log-viewer>',
                ncyBreadcrumb: {
                  skip: true
                }
              })
          .state('flows.detail.schedules', {
            url: '/schedules',
            templateUrl: '/assets/features/flows/templates/tabs/schedules.html',
            ncyBreadcrumb: {
              skip: true
            }
          })
          .state('flows.detail.metadata', {
            url: '/metadata',
            templateUrl: '/assets/features/flows/templates/tabs/metadata.html',
            ncyBreadcrumb: {
              skip: true
            }
          })
          .state('flows.detail.history', {
            url: '/history',
            templateUrl: '/assets/features/flows/templates/tabs/history.html',
            ncyBreadcrumb: {
              skip: true
            }
          })
          .state('flows.detail.resources', {
            url: '/resources',
            templateUrl: '/assets/features/flows/templates/tabs/resources.html',
            ncyBreadcrumb: {
              skip: true
            }
          });
  });
