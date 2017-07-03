(function() {
    'use strict';

    angular
        .module('productApp')
        .controller('JhipConfigurationController', JhipConfigurationController);

    JhipConfigurationController.$inject = ['$filter','JhipConfigurationService'];

    function JhipConfigurationController (filter,JhipConfigurationService) {
        var vm = this;

        vm.allConfiguration = null;
        vm.configuration = null;

        JhipConfigurationService.get().then(function(configuration) {
            vm.configuration = configuration;
        });
        JhipConfigurationService.getEnv().then(function (configuration) {
            vm.allConfiguration = configuration;
        });
    }
})();
