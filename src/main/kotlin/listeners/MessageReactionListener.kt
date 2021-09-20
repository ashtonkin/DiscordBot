/*
 * MIT License
 *
 * Copyright (c) 2021 Ashley Tonkin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package listeners

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageReactionListener : ListenerAdapter() {

    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (event.messageIdLong == 887938440195366922) {
            when (event.reactionEmote.asReactionCode) {
                "minecraft:887306002523901952" -> event.guild.addRoleToMember(
                    event.member!!,
                    event.guild.getRoleById(873565563077685309)!!
                ).queue()
                "Bot:887306146518536223" -> event.guild.addRoleToMember(
                    event.member!!,
                    event.guild.getRoleById(873565363105833002)!!
                ).queue()
                "\uD83D\uDCE2" -> event.guild.addRoleToMember(
                    event.member!!,
                    event.guild.getRoleById(873550638913556481)!!
                ).queue()
            }
            return
        }
    }

    override fun onMessageReactionRemove(event: MessageReactionRemoveEvent) {
        if (event.messageIdLong == 887938440195366922) {
            when (event.reactionEmote.asReactionCode) {
                "minecraft:887306002523901952" -> event.guild.removeRoleFromMember(
                    event.member!!,
                    event.guild.getRoleById(873565563077685309)!!
                ).queue()
                "Bot:887306146518536223" -> event.guild.removeRoleFromMember(
                    event.member!!,
                    event.guild.getRoleById(873565363105833002)!!
                ).queue()
                "\uD83D\uDCE2" -> event.guild.removeRoleFromMember(
                    event.member!!,
                    event.guild.getRoleById(873550638913556481)!!
                ).queue()
            }
            return
        }
    }

}