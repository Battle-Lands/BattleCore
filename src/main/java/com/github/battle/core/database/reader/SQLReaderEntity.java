package com.github.battle.core.database.reader;

import com.github.battle.core.serialization.IOUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public final class SQLReaderEntity {

    private final String parent;

    private final Map<String, String> rootParent = new HashMap<>();

    public String getLazyQuery(@NonNull Plugin plugin, @NonNull String path) {
        String queryContent = rootParent.get(path);
        if (queryContent != null) return queryContent;

        if (!path.endsWith(".sql")) path += ".sql";
        final String rawParentPath = String.format("sql/%s/%s", parent, path);

        final InputStream resourceStream = plugin.getResource(rawParentPath);
        queryContent = IOUtil.readInputStream(resourceStream);

        rootParent.put(path, queryContent);
        return queryContent;
    }
}
