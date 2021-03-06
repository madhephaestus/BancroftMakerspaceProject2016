import java.nio.file.Paths;
import eu.mihosoft.vrl.v3d.FileUtil;
import com.neuronrobotics.bowlerstudio.vitamins.*;

return { double keepawayLength ->
[servoBodyX = 19.7, servoBodyY = 39.7, servoBodyZ = 38.3]
[postDiameter = 8, postTaper = 0.6, postSpaceBetween = 4.2, postHeight = 14.3]
[screwDiameter = 4.5, screwLengthMax = 12.7, screwHeadDiameter = 6.62]
[postBracketX = 5, postBracketY = 1, postBracketZ = 11.4, postBracketTaper = 3.4]
[axleSlotX = 3.175, axleSlotY = 3.175, axleSlotZ = 5.2, axleSlotInsetY = 8, axleLength = 20]
[shoulderDiameter = 11.6, shoulderTaper = 1.9, shoulderHeight = 1, shoulderInsetY = 3.1]
[clutchHeight = 13.208,
 clutchDiameter = 16.2]

//Servo body
CSG servo = new Cube(servoBodyX, servoBodyY, servoBodyZ).toCSG()
			.makeKeepaway(1.5)

//Screw post
CSG post = new Cylinder(postDiameter / 2 + postTaper, postDiameter / 2, postHeight, 8).toCSG()
			.movez(servoBodyZ / 2)
			.movey(-servoBodyY / 2 + postDiameter / 2 + postTaper);

servo = servo.union(post)
servo = servo.union(post.movey(postDiameter + postTaper + postSpaceBetween))

//Screws
CSG screw = new Cylinder(screwDiameter / 2, screwDiameter / 2, screwLengthMax, 8).toCSG()
			.movey(-servoBodyY / 2 + postDiameter / 2 + postTaper)
			.movez(servoBodyZ / 2 + screwLengthMax);

CSG screwKeepaway = new Cylinder(screwHeadDiameter / 2, screwHeadDiameter / 2, keepawayLength, 8).toCSG()
			.movey(-servoBodyY / 2 + postDiameter / 2 + postTaper)
			.movez(servoBodyZ / 2 + screwLengthMax * 2);
			
screw = screw.union(screwKeepaway)

servo = servo.union(screw)
			.union(screw.movey(postDiameter + postTaper + postDiameter / 2));

//Bracket between screw posts
postBracket = new Cube(postBracketX + 2, postSpaceBetween + 3, postBracketZ).toCSG()
			.movey(-servoBodyY / 2 + postDiameter + postSpaceBetween / 2)
			.movez(servoBodyZ - postHeight);

servo = servo.union(postBracket)

//Axle shoulder
CSG shoulder = new Cylinder(shoulderDiameter / 2 + shoulderTaper / 2, shoulderDiameter / 2, shoulderHeight, 8).toCSG()
				.movey(servoBodyY / 2 - shoulderDiameter / 2 - shoulderTaper / 2 - shoulderInsetY)
				.movez(servoBodyZ / 2);

servo = servo.union(shoulder);

//Clutch
CSG clutch = new Cylinder(clutchDiameter / 2, clutchDiameter / 2, clutchHeight, 8).toCSG()
				.movey(servoBodyY / 2 - shoulderDiameter / 2 - shoulderTaper / 2 - shoulderInsetY)
				.movez(servoBodyZ / 2)
				.makeKeepaway(1.5)

servo = servo.union(clutch);

//Axle
CSG axle = new Cylinder((axleSlotX * 1.414) / 2, (axleSlotX * 1.414) / 2, axleLength, 8).toCSG()
			.movey(servoBodyY / 2 - shoulderDiameter / 2 - shoulderTaper / 2 - shoulderInsetY)
			.movez(servoBodyZ - clutchHeight / 2);


servo = servo.union(axle);

return servo.roty(180).movey(-servoBodyY / 4).movez(servoBodyZ / 2 + clutchHeight)
}