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
class Database(dbName: String, dbUser: String, dbPass: String) {

    // Logger
    companion object {
        val logger: Logger = LoggerFactory.getLogger(Database::class.java)
    }

    private val dbUrl = "jdbc:mariadb://localhost:3306/$dbName"
    private val dbDriver = "org.mariadb.jdbc.Driver"
    private val dbConnection: Connection

    init {
        Class.forName(dbDriver)
        dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPass)
    }

    // Insert a new row into the database
    fun insert(table: String, values: Map<String, Any?>): ResultSet {
        val sql = "INSERT INTO $table " +
                "(" + values.keys.joinToString(", ") + ") " +
                "VALUES " +
                "(" + values.values.map { if (it == null) "NULL" else "'$it'" }.joinToString(", ") + ")"
        logger.info("SQL: $sql")
        return dbConnection.prepareStatement(sql).executeQuery()
    }

    // Update a row in the database
    fun update(table: String, values: Map<String, Any?>, where: String): ResultSet {
        val sql = "UPDATE $table " +
                "SET " + values.map { "${it.key} = ${if (it.value == null) "NULL" else "'${it.value}'"}" }.joinToString(", ") + " " +
                "WHERE $where"
        logger.info("SQL: $sql")
        return dbConnection.prepareStatement(sql).executeQuery()
    }

    // Delete a row from the database
    fun delete(table: String, where: String): ResultSet {
        val sql = "DELETE FROM $table WHERE $where"
        logger.info("SQL: $sql")
        return dbConnection.prepareStatement(sql).executeQuery()
    }

    // Select a row from the database
    fun select(table: String, where: String): ResultSet {
        val sql = "SELECT * FROM $table WHERE $where"
        logger.info("SQL: $sql")
        return dbConnection.prepareStatement(sql).executeQuery()
    }

    // Select all rows from the database
    fun selectAll(table: String): ResultSet {
        val sql = "SELECT * FROM $table"
        logger.info("SQL: $sql")
        return dbConnection.prepareStatement(sql).executeQuery()
    }

    // Get Value from the database and return it as a string
    fun getValue(table: String, column: String, where: String): String {
        val sql = "SELECT $column FROM $table WHERE $where"
        logger.info("SQL: $sql")
        val rs = dbConnection.prepareStatement(sql).executeQuery()
        if (rs.next()) {
            return rs.getString(1)
        }
        return ""
    }

    // Close the database connection
    fun close() {
        logger.info("Closing database connection")
        dbConnection.close()
    }

    // Return the database connection
    fun getConnection(): Connection {
        logger.info("Database connected")
        return dbConnection
    }

}

