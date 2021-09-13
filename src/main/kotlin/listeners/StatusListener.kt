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

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class StatusListener : ListenerAdapter() {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(StatusListener::class.java)
    }

    /**
     * Sets the presence to the member count when the guild starts
     */
    override fun onGuildReady(event: GuildReadyEvent) {
        updatePresence(event.jda, event.guild.memberCount)
    }

    /**
     * Updates the member count when a player joins the server
     */
    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        updatePresence(event.jda, event.guild.memberCount)
    }

    /**
     * Updates the member count when a player leaves the server
     */
    override fun onGuildMemberRemove(event: GuildMemberRemoveEvent) {
        updatePresence(event.jda, event.guild.memberCount)
    }

    /**
     * Updates the presence to the passed member count
     */
    private fun updatePresence(jda: JDA, members: Int) =
        jda.presence.setPresence(Activity.watching("$members members"), false)

}