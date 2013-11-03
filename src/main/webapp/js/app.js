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

angular.module('ebook', []).
        config(['$routeProvider', function ($routeProvider) {
            $routeProvider.
                    when('/', {templateUrl: 'views/index-detail.html', controller: CtrlEbooks}).
                    otherwise({redirectTo: '/'});
        }]).filter('epub', function () {
                       return function (input) {
                           console.info(input);

                           //return input.substr(0, input.length - 5);

                       }
                   });