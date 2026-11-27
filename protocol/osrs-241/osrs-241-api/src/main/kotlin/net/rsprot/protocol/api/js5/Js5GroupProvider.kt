package net.rsprot.protocol.api.js5

import io.netty.buffer.ByteBuf

/**
 * The group provider interface for JS5.
 */
public fun interface Js5GroupProvider {
    /**
     * Provides a JS5 group based on the input archive and group
     * @param archive the archive id requested by the client
     * @param group the group in that archive requested
     * @return a full JS5 group to be written to the client
     */
    public fun provide(
        archive: Int,
        group: Int,
    ): ByteBuf?

    /**
     * Gets the number of bytes the buffer returned by [provide] for this group would contain.
     * This is only used for scheduling decisions (prefetch throttling and ordering of urgent
     * requests), so an approximation is acceptable, as long as it is consistent between groups.
     *
     * The default implementation calls [provide] and reads the size of the returned buffer.
     * Providers for which [provide] is expensive (e.g. groups read and encoded from disk on
     * demand) should override this with a cheap lookup, as it may be called several times
     * per served group.
     * @param archive the archive id requested by the client
     * @param group the group in that archive requested
     * @return the size of the group in bytes, or 0 if the group does not exist.
     */
    public fun size(
        archive: Int,
        group: Int,
    ): Int = provide(archive, group)?.readableBytes() ?: 0
}
