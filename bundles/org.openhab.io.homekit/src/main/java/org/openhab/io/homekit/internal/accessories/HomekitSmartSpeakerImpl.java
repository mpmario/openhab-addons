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
package org.openhab.io.homekit.internal.accessories;

import static org.openhab.io.homekit.internal.HomekitCharacteristicType.CURRENT_MEDIA_STATE;
import static org.openhab.io.homekit.internal.HomekitCharacteristicType.TARGET_MEDIA_STATE;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.openhab.core.library.items.StringItem;
import org.openhab.core.library.types.StringType;
import org.openhab.io.homekit.internal.HomekitAccessoryUpdater;
import org.openhab.io.homekit.internal.HomekitSettings;
import org.openhab.io.homekit.internal.HomekitTaggedItem;

import io.github.hapjava.accessories.SmartSpeakerAccessory;
import io.github.hapjava.characteristics.HomekitCharacteristicChangeCallback;
import io.github.hapjava.characteristics.impl.television.CurrentMediaStateEnum;
import io.github.hapjava.characteristics.impl.television.TargetMediaStateEnum;
import io.github.hapjava.services.impl.SmartSpeakerService;

/**
 *
 * @author Eugen Freiter - Initial contribution
 */
public class HomekitSmartSpeakerImpl extends AbstractHomekitAccessoryImpl implements SmartSpeakerAccessory {
    private final Map<CurrentMediaStateEnum, String> currentMediaState;
    private final Map<TargetMediaStateEnum, String> targetMediaState;

    public HomekitSmartSpeakerImpl(HomekitTaggedItem taggedItem, List<HomekitTaggedItem> mandatoryCharacteristics,
            HomekitAccessoryUpdater updater, HomekitSettings settings) {
        super(taggedItem, mandatoryCharacteristics, updater, settings);
        currentMediaState = new EnumMap<>(CurrentMediaStateEnum.class);
        currentMediaState.put(CurrentMediaStateEnum.STOP, "STOP");
        currentMediaState.put(CurrentMediaStateEnum.PLAY, "PLAY");
        currentMediaState.put(CurrentMediaStateEnum.PAUSE, "PAUSE");
        currentMediaState.put(CurrentMediaStateEnum.UNKNOWN, "UNKNOWN");
        updateMapping(CURRENT_MEDIA_STATE, currentMediaState);

        targetMediaState = new EnumMap<>(TargetMediaStateEnum.class);
        targetMediaState.put(TargetMediaStateEnum.STOP, "STOP");
        targetMediaState.put(TargetMediaStateEnum.PLAY, "PLAY");
        targetMediaState.put(TargetMediaStateEnum.PAUSE, "PAUSE");
        updateMapping(TARGET_MEDIA_STATE, targetMediaState);
        getServices().add(new SmartSpeakerService(this));
    }

    @Override
    public CompletableFuture<CurrentMediaStateEnum> getCurrentMediaState() {
        return CompletableFuture.completedFuture(
                getKeyFromMapping(CURRENT_MEDIA_STATE, currentMediaState, CurrentMediaStateEnum.UNKNOWN));
    }

    @Override
    public void subscribeCurrentMediaState(final HomekitCharacteristicChangeCallback callback) {
        subscribe(CURRENT_MEDIA_STATE, callback);
    }

    @Override
    public void unsubscribeCurrentMediaState() {
        unsubscribe(CURRENT_MEDIA_STATE);
    }

    @Override
    public CompletableFuture<TargetMediaStateEnum> getTargetMediaState() {
        return CompletableFuture
                .completedFuture(getKeyFromMapping(TARGET_MEDIA_STATE, targetMediaState, TargetMediaStateEnum.STOP));
    }

    @Override
    public CompletableFuture<Void> setTargetMediaState(final TargetMediaStateEnum targetState) {
        getItem(TARGET_MEDIA_STATE, StringItem.class)
                .ifPresent(item -> item.send(new StringType(targetMediaState.get(targetState))));
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void subscribeTargetMediaState(final HomekitCharacteristicChangeCallback callback) {
        subscribe(TARGET_MEDIA_STATE, callback);
    }

    @Override
    public void unsubscribeTargetMediaState() {
        unsubscribe(TARGET_MEDIA_STATE);
    }
}
