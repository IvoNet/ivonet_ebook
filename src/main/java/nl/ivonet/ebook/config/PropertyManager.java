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

package nl.ivonet.ebook.config;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertyManager {
    static final String INVALID_KEY = "Invalid key '{0}'";
    static final String MANDATORY_PARAM_MISSING = "No definition found for a mandatory configuration parameter : '{0}'";
    private final String BUNDLE_FILE_NAME = "application";
    private final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_FILE_NAME);

    @Produces
    @Property
    public String injectConfiguration(final InjectionPoint injectionPoint) throws IllegalStateException {
        final Property property = injectionPoint.getAnnotated().getAnnotation(Property.class);
        if (empty(property.key())) {
            return property.defaultValue();
        }
        final String value;
        try {
            value = this.bundle.getString(property.key());
            if (empty(value)) {
                if (property.mandatory()) {
                    throw new IllegalStateException(MessageFormat.format(MANDATORY_PARAM_MISSING, new Object[]{
                            property.key()}));
                } else {
                    return property.defaultValue();
                }
            }
            return value;
        } catch (MissingResourceException e) {
            if (property.mandatory()) {
                throw new IllegalStateException(MessageFormat.format(MANDATORY_PARAM_MISSING,
                                                                     new Object[]{property.key()}));
            }
            return MessageFormat.format(INVALID_KEY, property.key());
        }
    }

    private boolean empty(final String value) {
        return value == null || value.trim().isEmpty();
    }
}
