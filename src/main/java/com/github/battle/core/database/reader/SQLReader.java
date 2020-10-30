package com.github.battle.core.database.reader;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

/**
 * @author SaiintBrisson (c)
 */
@Getter
@RequiredArgsConstructor
public final class SQLReader {

    @NonNull
    private final Plugin plugin;

    private final HashMap<String, SQLReaderEntity> sqlReaderEntities = new HashMap<>();

    public String getQuery(@NonNull String path) {
        if (!path.contains(".")) {
            return getRootParent().getLazyQuery(this.plugin, path);
        }

        final String[] constraintPathKeys = path.split("\\.");
        return getQueryParent(this.plugin, constraintPathKeys[0])
          .getLazyQuery(this.plugin, constraintPathKeys[1]);
    }

    public SQLReaderEntity getRootParent() {
        return getQueryParent(this.plugin,"root");
    }

    public SQLReaderEntity getQueryParent(@NonNull Plugin plugin, @NonNull String parent) {
        SQLReaderEntity sqlReaderEntity = sqlReaderEntities.get(parent);
        if (sqlReaderEntity != null) return sqlReaderEntity;

        sqlReaderEntity = new SQLReaderEntity(parent);
        sqlReaderEntities.put(parent, sqlReaderEntity);

        return sqlReaderEntity;
    }
}
