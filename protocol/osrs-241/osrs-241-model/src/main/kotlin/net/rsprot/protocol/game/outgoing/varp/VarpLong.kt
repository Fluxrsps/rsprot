package net.rsprot.protocol.game.outgoing.varp

import net.rsprot.protocol.ServerProtCategory
import net.rsprot.protocol.game.outgoing.GameServerProtCategory
import net.rsprot.protocol.message.OutgoingGameMessage

/**
 * Varp long messages are used to send a 64-bit varp value to the client.
 * @property id the id of the varp
 * @property value the value of the varp
 */
public class VarpLong(
    public val id: Int,
    public val value: Long,
) : OutgoingGameMessage {
    override val category: ServerProtCategory
        get() = GameServerProtCategory.HIGH_PRIORITY_PROT

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VarpLong

        if (id != other.id) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    override fun toString(): String =
        "VarpLong(" +
            "id=$id, " +
            "value=$value" +
            ")"
}
