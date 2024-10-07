from flask import Flask, jsonify, request
from pyspark.sql import SparkSession

# Initialize Flask app
app = Flask(__name__)

# Initialize Spark session
spark = SparkSession.builder \
    .appName("SparkDataProcessing") \
    .master("spark://spark-master:7077") \
    .getOrCreate()


@app.route('/api/data', methods=['GET'])
def get_processed_data():
    # Here you can perform data processing using Spark
    # For example, loading a CSV file from the Spark container's file system
    df = spark.read.csv('/opt/spark-data/input.csv', header=True, inferSchema=True)

    # Perform a simple transformation, e.g., show the first 5 rows
    top_rows = df.limit(5).collect()

    # Convert to JSON-friendly format
    data = [row.asDict() for row in top_rows]

    return jsonify(data)


@app.route('/api/process', methods=['POST'])
def process_data():
    # Assume we're receiving some data for Spark processing
    content = request.get_json()

    # Do something with the incoming data, e.g., saving it to a Spark DataFrame
    df = spark.createDataFrame(content)

    # Perform transformations, e.g., count rows
    row_count = df.count()

    return jsonify({"message": "Data processed", "row_count": row_count})


if __name__ == '__main__':
    # Flask will run on port 8080
    app.run(host='0.0.0.0', port=8080)
