(function() {
    'use strict';

    angular
        .module('salon123App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('audits', {
            parent: 'admin',
            url: '/audits',
            data: {
                authorities: ['ADMIN'],
                pageTitle: 'audits.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/audits/audits.html',
                    controller: 'AuditsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('audits');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
