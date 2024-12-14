###############################################################################
# Project-specific settings
###############################################################################

# Shows debug messages while Silence is running
DEBUG_ENABLED = False

# Database connection details
DB_CONN = {
    "host": "127.0.0.1",
    "port": 3306,
    "username": "root",
    "password": "root",
    "database": "costswatcher_silence_ext",
}

# The sequence of SQL scripts located in the sql/ folder that must
# be ran when the 'silence createdb' command is issued
SQL_SCRIPTS = [
    "create_db.sql"
]

# The port in which the API and the web server will be deployed
HTTP_PORT = 8081

# The URL prefix for all API endpoints
API_PREFIX = "/silence-api/"

# A random string that is used for security purposes
# (this has been generated automatically upon project creation)
SECRET_KEY = "-T2WorGyLLAEyVkNc49MQgVqa8w5mLs7gGCptjlXKu8"
