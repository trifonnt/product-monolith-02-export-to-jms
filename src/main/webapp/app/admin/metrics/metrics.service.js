(function() {
    'use strict';

    angular
        .module('productApp')
        .factory('JhipMetricsService', JhipMetricsService);

    JhipMetricsService.$inject = ['$rootScope', '$http'];

    function JhipMetricsService ($rootScope, $http) {
        var service = {
            getMetrics: getMetrics,
            threadDump: threadDump
        };

        return service;

        function getMetrics () {
            return $http.get('management/metrics').then(function (response) {
                return response.data;
            });
        }

        function threadDump () {
            return $http.get('management/dump').then(function (response) {
                return response.data;
            });
        }
    }
})();
