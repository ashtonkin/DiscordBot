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

package utils

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import java.time.LocalDateTime

class EmbedUtils(user: User? = null, timestamp: Boolean = false) {

    private val embed = EmbedBuilder()
    private var footer: String = ""
    private var userImage = user?.avatarUrl ?: user?.defaultAvatarUrl

    constructor(timestamp: Boolean) : this(null, timestamp)

    init {
        embed.setColor(Utils.hex2rgb(Color.DEFAULT.code))

        if (user != null) {
            footer = "Requested by: ${user.asTag}"
            embed.setFooter(footer, userImage)
        }
        embed.setTimestamp(LocalDateTime.now())
    }

    fun title(title: String): EmbedUtils {
        embed.setTitle(title)
        return this
    }

    fun footer(footer: String): EmbedUtils {
        embed.setFooter(footer)
        return this
    }

    fun footer(footer: String, icon: String): EmbedUtils {
        embed.setFooter(footer, icon)
        return this
    }

    fun appendFooter(footer: String): EmbedUtils {
        this.footer += footer
        embed.setFooter(this.footer, userImage)
        return this
    }

    fun color(color: String): EmbedUtils {
        embed.setColor(Utils.hex2rgb(color))
        return this
    }

    fun color(color: Color): EmbedUtils {
        embed.setColor(Utils.hex2rgb(color.code))
        return this
    }

    fun field(title: String, body: String): EmbedUtils {
        embed.addField(title, body, false)
        return this
    }

    fun field(field: MessageEmbed.Field): EmbedUtils {
        embed.addField(field)
        return this
    }

    fun field(title: String, body: String, inline: Boolean): EmbedUtils {
        embed.addField(title, body, inline)
        return this
    }

    fun thumbnail(url: String): EmbedUtils {
        embed.setThumbnail(url)
        return this
    }

    fun empty(): EmbedUtils {
        embed.addBlankField(false)
        return this
    }

    fun image(url: String): EmbedUtils {
        embed.setImage(url)
        return this
    }

    fun author(author: String): EmbedUtils {
        embed.setAuthor(author)
        return this
    }

    fun author(author: String, image: String): EmbedUtils {
        embed.setAuthor(author, null, image)
        return this
    }

    fun description(description: String): EmbedUtils {
        embed.setDescription(description)
        return this
    }

    fun build(): MessageEmbed {
        return embed.build()
    }

}