/*
 * Copyright (c) 2013 by Ivo Woltring (http://ivonet.nl)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

var app = angular.module('ebook', [])

app.config(['$routeProvider', function ($routeProvider) {
   $routeProvider.
         when('/', {templateUrl: 'views/index-detail.html', controller: CtrlEbooks}).
         otherwise({redirectTo: '/'});
}]);

app.directive('focusOn', function () {
   return function (scope, elem, attr) {
      scope.$on('focusOn', function (e, name) {
         if (name === attr.focusOn) {
            elem[0].focus();
         }
      });
   };
});

app.factory('focus', function ($rootScope, $timeout) {
   return function (name) {
      $timeout(function () {
         $rootScope.$broadcast('focusOn', name);
      });
   }
});