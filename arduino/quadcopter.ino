
#include <Servo.h>
Servo myservo;
int up = 150;
int down = 60;

void setup()
{
  myservo.attach(9);
}

void loop()
{
  myservo.write(up);
  delay(2000);
  myservo.write(down);
  delay(2000);

}

