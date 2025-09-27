import time
import mysql.connector
from mysql.connector import Error

# Configuration de la connexion MySQL
config = {
    'user': 'root',
    'password': 'QylWbngY2gm3OTO4BUBJtn8Ik1iTPeaCIhptoEXJRaouJH3rPs7kcNC3pjDA',
    'host': 'mysql',
    'port': '3307',
    'database': 'challenge_db'
}

def connect_to_db():
    connection = None
    for attempt in range(10):
        try:
            connection = mysql.connector.connect(**config)
            if connection.is_connected():
                cursor = connection.cursor()
                cursor.execute("CREATE TABLE IF NOT EXISTS flag_table (flag VARCHAR(255) PRIMARY KEY);")
                return connection
        except Error as err:
            time.sleep(5)
    raise Exception("Could not connect to MySQL after 10 attempts")

def check_max_connections():
    try:
        connection = connect_to_db()
        cursor = connection.cursor()
        cursor.execute("SHOW VARIABLES LIKE 'max_connections';")
        result = cursor.fetchone()
        if result:
            if result[1] == '4':
                cursor.execute("INSERT INTO flag_table (flag) VALUES ('FLAG-kampeurs')")
                connection.commit()
        cursor.close()
        connection.close()
    except Error as err:
        print("Error: ", err)

while True:
    check_max_connections()
    time.sleep(2)
