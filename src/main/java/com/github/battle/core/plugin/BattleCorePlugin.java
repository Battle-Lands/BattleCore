package com.github.battle.core.plugin;

import com.github.battle.core.common.CredentialRegistry;

public class BattleCorePlugin extends PluginCore {

    @Override
    public void onPluginEnable() {
        saveDefaultConfig();
        info(getDescription().getDescription());

        final CredentialRegistry credentialRegistry = new CredentialRegistry(getConfig());
        registerService(credentialRegistry);
    }
}
