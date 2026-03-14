from flask import Flask, request, jsonify
import bcrypt

app = Flask(__name__)

users = {}

@app.route("/", methods=["GET"])
def home():
    return jsonify({
        "message": "API activa"
    })


@app.route("/register", methods=["POST"])
def register():

    data = request.get_json()

    username = data.get("username")
    password = data.get("password")

    if username in users:
        return jsonify({
            "message": "El usuario ya existe"
        }), 409

    hashed = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())

    users[username] = hashed

    return jsonify({
        "message": "Usuario registrado correctamente"
    }), 201


@app.route("/login", methods=["POST"])
def login():

    data = request.get_json()

    username = data.get("username")
    password = data.get("password")

    if username not in users:
        return jsonify({
            "message": "Credenciales incorrectas"
        }), 401

    stored_hash = users[username]

    if bcrypt.checkpw(password.encode('utf-8'), stored_hash):

        return jsonify({
            "message": "Login exitoso",
            "username": username
        })

    else:

        return jsonify({
            "message": "Credenciales incorrectas"
        }), 401


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)