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

package database

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

// Create a new database class and database and connect to it
class Database(private val dbName: String, private val dbUser: String, private val dbPass: String) {

    // Logger
    companion object {
        val logger: Logger = LoggerFactory.getLogger(Database::class.java)
    }

    private val dbUrl = "jdbc:mysql://localhost:3306/$dbName?useSSL=false"
    private val dbDriver = "com.mysql.jdbc.Driver"
    private val dbConnection: Connection

    init {
        Class.forName(dbDriver)
        dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPass)
    }

    // Insert a new row into the database
    fun insert(table: String, columns: List<String>, values: List<String>) {
        val query = "INSERT INTO $table (${columns.joinToString(",")}) VALUES (${values.joinToString(",")})"
        logger.info("Inserting into $table: $query")
        val statement = dbConnection.createStatement()
        statement.executeUpdate(query)
    }

    // Update a row in the database
    fun update(table: String, columns: List<String>, values: List<String>, where: String) {
        val query = "UPDATE $table SET ${columns.joinToString(",")} = ${values.joinToString(",")} WHERE $where"
        logger.info("Updating $table: $query")
        val statement = dbConnection.createStatement()
        statement.executeUpdate(query)
    }

    // Delete a row from the database
    fun delete(table: String, where: String) {
        val query = "DELETE FROM $table WHERE $where"
        logger.info("Deleting from $table: $query")
        val statement = dbConnection.createStatement()
        statement.executeUpdate(query)
    }

    // Select a row from the database
    fun select(table: String, columns: List<String>, where: String): ResultSet {
        val query = "SELECT ${columns.joinToString(",")} FROM $table WHERE $where"
        logger.info("Selecting from $table: $query")
        val statement = dbConnection.createStatement()
        return statement.executeQuery(query)
    }

    // Get information from the database
    fun getInfo(column: String, table: String, where: String): String {
        val query = "SELECT $column FROM $table WHERE $where"
        logger.info("Getting info from $table: $query")
        val statement = dbConnection.createStatement()
        val result = statement.executeQuery(query)
        result.next()
        return result.getString(column)
    }

    // Close the database connection
    fun close() {
        logger.info("Closing database connection")
        dbConnection.close()
    }

    // Return the database connection
    fun getConnection(): Connection {
        logger.info("Returning database connection")
        return dbConnection
    }

}

