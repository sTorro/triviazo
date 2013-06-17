/*
 * This file is part of Triviazo project.
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with triviazo-project.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2013 Sergio Torró.
 */

package com.torrosoft.triviazo.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is a simple implementation of the LRU (Least Recently Used) cache
 * algorithm with the goal of optimize the execution. It's used by the Sound
 * service.
 * 
 * <a href="http://en.wikipedia.org/wiki/Cache_algorithms#Least_Recently_Used">
 * For more information see Wikipedia</a>
 * 
 * @author Sergio Torró
 * @since 09/05/2013
 * @version 0.1
 */
public class LRUCache<K, V> {
    private Map<K, V> cache;
    private CacheEntryRemovedListener<K, V> entryRemovedListener;

    /**
     * Creates the cache with the specified max entries.
     */
    public LRUCache(final int maxEntries) {
        cache = new LinkedHashMap<K, V>(maxEntries + 1, .75F, true) {
            /**
             * Serial version UID.
             */
            private static final long serialVersionUID = -3773718296246105356L;

            @Override
            public boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
                if (size() > maxEntries) {
                    if (entryRemovedListener != null) {
                        entryRemovedListener.notifyEntryRemoved(
                                eldest.getKey(), eldest.getValue());
                    }

                    return true;
                }

                return false;
            }
        };
    }

    public final void add(final K key, final V value) {
        cache.put(key, value);
    }

    public final V get(final K key) {
        return cache.get(key);
    }

    public final Collection<V> retrieveAll() {
        return cache.values();
    }

    public final void setEntryRemovedListener(
            final CacheEntryRemovedListener<K, V> pEntryRemovedListener) {
        entryRemovedListener = pEntryRemovedListener;
    }

    /**
     * Called when a cached element is about to be removed.
     */
    public interface CacheEntryRemovedListener<K, V> {
        void notifyEntryRemoved(K key, V value);
    }
}
