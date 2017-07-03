(function() {
    'use strict';

    angular
        .module('productApp')
        .controller('JhipLanguageController', JhipLanguageController);

    JhipLanguageController.$inject = ['$translate', 'JhipLanguageService', 'tmhDynamicLocale'];

    function JhipLanguageController ($translate, JhipLanguageService, tmhDynamicLocale) {
        var vm = this;

        vm.changeLanguage = changeLanguage;
        vm.languages = null;

        JhipLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

        function changeLanguage (languageKey) {
            $translate.use(languageKey);
            tmhDynamicLocale.set(languageKey);
        }
    }
})();
