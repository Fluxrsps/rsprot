package net.rsprot.protocol.common.game.outgoing.inv

/**
 * Inventory object is a helper object built around the primitive 'long' type.
 * We utilize longs directly here to reduce the effects of garbage creation
 * via inventories, as this can otherwise get quite severe with a lot of players.
 */
public object InventoryObject {
    public const val NULL: Long = -1

    // Fluxious: obj ids are 32-bit on the wire, and unified RS3 obj ids (1048576 + rs3 id)
    // need 21 bits. The low 32 bits are split into an 11-bit slot (0..2046, 2047 = -1) and a
    // 21-bit id (0..2097150, 2097151 = -1); the high 32 bits stay the count.
    private const val SLOT_BITS: Int = 11
    private const val SLOT_MASK: Long = (1L shl SLOT_BITS) - 1
    private const val ID_MASK: Long = (1L shl (32 - SLOT_BITS)) - 1

    @JvmSynthetic
    public operator fun invoke(
        id: Int,
        count: Int,
    ): Long = pack(0, id, count)

    @JvmSynthetic
    public operator fun invoke(
        slot: Int,
        id: Int,
        count: Int,
    ): Long = pack(slot, id, count)

    @JvmStatic
    public fun pack(
        id: Int,
        count: Int,
    ): Long = pack(0, id, count)

    @JvmStatic
    public fun pack(
        slot: Int,
        id: Int,
        count: Int,
    ): Long =
        (slot.toLong() and SLOT_MASK)
            .or((id.toLong() and ID_MASK) shl SLOT_BITS)
            .or((count.toLong() and 0xFFFFFFFF) shl 32)

    @JvmStatic
    public fun getSlot(packed: Long): Int {
        val value = (packed and SLOT_MASK).toInt()
        return if (value.toLong() == SLOT_MASK) {
            -1
        } else {
            value
        }
    }

    @JvmStatic
    public fun getId(packed: Long): Int {
        val value = (packed ushr SLOT_BITS and ID_MASK).toInt()
        return if (value.toLong() == ID_MASK) {
            -1
        } else {
            value
        }
    }

    @JvmStatic
    public fun getCount(packed: Long): Int = (packed ushr 32).toInt()
}
