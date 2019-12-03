#include <ESP8266WiFi.h>

const char* ssid = "TP-LINK_0E9C";
const char* password = "pramukhnik86";

// Create an instance of the server
// specify the port to listen on as an argument
WiFiServer server(80);

void setup() {
  Serial.begin(9600);
  delay(10);

  // prepare GPIO2
  pinMode(2, OUTPUT);
  digitalWrite(2, 0);

  // prepare GPIO0
  pinMode(0, OUTPUT);
  digitalWrite(0, 0);

  // Connect to WiFi network
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");

  // Start the server
  server.begin();
  Serial.println("Server started");

  // Print the IP address
  Serial.println(WiFi.localIP());
}

void loop() {
  // Check if a client has connected
  WiFiClient client = server.available();
  if (!client) {
    return;
  }

  // Wait until the client sends some data
  Serial.println("new client");
  while (!client.available()) {
    delay(1);
  }

  // Read the first line of the request
  String req = client.readStringUntil('\r');
  Serial.println(req);
  client.flush();

  // Match the request
  int val, val2;
  if (req.indexOf("/down") != -1)
    val = 0;
  else if (req.indexOf("/up") != -1)
    val = 1;
  else if (req.indexOf("/right") != -1)
    val2 = 1;
  else if (req.indexOf("/left") != -1)
    val2 = 0;
  else {
    Serial.println("invalid request");
    client.stop();
    return;
  }

  // Set GPIO according to the request
  digitalWrite(2, val);
  digitalWrite(0, val2);

  client.flush();

  // Prepare the response
  String s = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<!DOCTYPE HTML>\r\n<html>\r\nQuad is now going ";
  String s2 = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<!DOCTYPE HTML>\r\n<html>\r\nQuad is now going ";
  s += (val) ? "up" : "down";
  s2 += (val2) ? " right" : " left";
  s += "</html>\n";
  s2 += "</html>\n";

  // Send the response to the client
    client.print(s);
    client.print(s2);
  delay(1);
  Serial.println("Client disonnected");

  // The client will actually be disconnected
  // when the function returns and 'client' object is detroyed
}

