/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.meteoalerte.internal.json;

import java.util.Optional;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link Parameters} is the Java class used to map the JSON
 * response to the webservice request.
 *
 * @author Gaël L'hopital - Initial contribution
 */
@NonNullByDefault
public class Parameters {
    private String dataset = "";
    @SerializedName("timezone")
    private String timeZone = "";
    private int rows;
    private String format = "";
    private @Nullable Refine refine;

    public String getDataset() {
        return dataset;
    }

    public String getTimezone() {
        return timeZone;
    }

    public int getRows() {
        return rows;
    }

    public String getFormat() {
        return format;
    }

    public Optional<Refine> getRefine() {
        Refine refine = this.refine;
        if (refine != null) {
            return Optional.of(refine);
        }
        return Optional.empty();
    }
}
