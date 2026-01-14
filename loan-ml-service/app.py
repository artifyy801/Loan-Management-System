from flask import Flask, request, jsonify
import numpy as np
from sklearn.linear_model import LogisticRegression

app = Flask(__name__)

print("Training ML model")

X_train = []
y_train = []

for i in range(1000):
    salary = np.random.randint(20000, 100000)
    loan_amount = np.random.randint(10000, 250000)

    if loan_amount <= (salary * 2):
        X_train.append([salary, loan_amount])
        y_train.append(1)
    else:
        X_train.append([salary, loan_amount])
        y_train.append(0)

model = LogisticRegression()
model.fit(X_train, y_train)
print("Model Trained and ready")


@app.route('/predict', methods=['POST'])
def predict():
    data = request.json
    salary = data.get('salary')
    amount = data.get('amount')

    if not salary or not amount:
        return jsonify({"error": "Missing salary or amount"}), 400

    prediction = model.predict([[salary, amount]])[0]
    probability = model.predict_proba([[salary, amount]])[0][1]

    result = "Approved" if prediction == 1 else "Rejected"

    return jsonify({
        "status": result,
        "confidence": round(probability * 100, 2),
        "message": f"Based on salary {salary},loan{amount} is {result}"
    })


if __name__ == "__main__":
    app.run(port=5000)
