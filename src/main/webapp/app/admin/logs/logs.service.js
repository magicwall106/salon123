(function() {
    'use strict';

    angular
        .module('salon123App')
        .factory('LogsService', LogsService);

    LogsService.$inject = ['$resource'];

    function LogsService ($resource) {
        var service = $resource('management/salon123/logs', {}, {
            'findAll': { method: 'GET', isArray: true},
            'changeLevel': { method: 'PUT'}
        });

        return service;
    }
})();
