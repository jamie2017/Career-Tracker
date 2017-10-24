/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.JMYE.myapplication.backend;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {
    static Logger Log = Logger.getLogger("com.example.JMYE.myapplication.backend.MyServlet");

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Log.info("Sending the todo list email.");

        String outString;
        outString = "<p>Sending the todo list email.</p><p><strong>Note:</strong> ";
        outString = outString.concat("the servlet must be deployed to App Engine in order to ");
        outString = outString.concat("send the email. Running the server locally writes a message ");
        outString = outString.concat("to the log file instead of sending an email message.</p>");

        resp.getWriter().println(outString);

        // Note: Ensure that the [PRIVATE_KEY_FILENAME].json has read
        // permissions set.
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(getServletContext().getResourceAsStream("/WEB-INF/\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDTLWLXdvVmTd55\\nNA2Eh1zw5qb5REulCviSMvvQaiEGmux7GupaLFTnLK9sirtlr8LfdzTIIP0iAm/O\\nGZ6w0IVXccBxraxqtrqbY6Q+8IcR+JCbbg7x2UaQ92oL2nW/jijdSBIPTG69Ba25\\nYKmFP4cgJTCyed20pxHq3zPBA5D9DYqla76nbscCc9d0iPfeZRE35vQAtbocFbwQ\\nH0A0s9S7b5Yv5M4jzS3NdlU1PnhqvU9bAMLRVYewZqYywdCMyoC2LaN9RxldQqg6\\noWqRyb0XBU0w/blicGPKWnnXihKuoW+koM/QubHS+xdmnG35kY76xxKo+y9uuszC\\ndFJCRvaZAgMBAAECggEAPNiP6Ssia/a1ylBgMLZb5iFfLOUrsoMlueIswhFCMMgE\\nqTfJHHA3imBKr0iN1C+/Q2nycFNo/BqdolGpcqKyOM7Sty5mRl0eCVW9ZlEhaeEn\\nsVEVx0NmXLCdAotQcAAC8Dcq1POd2KLBOm+KiJUUVwPAMzx/z+RvejATlmJzrSVq\\nXul+UXgS3sX84bgpAfwjRsMj0LhrERBwe1wE1IojP4bYIea7mnf+4i/fAgRK9OXB\\nTKA0L0DuIctAVoRUktGqOgq92rgHfA60WrXPjIkom+rwbs3wg7T1WAj7nrRHgra/\\n6uIFoGZPk1AXMnnT7W8RIFUpy55119yQ+cO4UXU8cQKBgQD51i36Fg7Tf4mW+ArR\\n3rtjJ+3H3Mm1KEwF7tXaqbFCInMIX/Q+0Z8/PlrmriwK5kJXzYvuI9U+Lb9+Mcjr\\nvuRB661NAQz3NOxc7BR1V+iUjk4JjlK/pumr4ORkNihjh4Xx8lTEkylcvC02QmEC\\n4VhxGlpXbPQtar0kqEOMn0fPbQKBgQDYYw6SfPrBg750D83sKW4zeta+MY6q5if3\\nj0Trkz1cAed6KGbrrup3dPH1Lc65QkHqcAfOfU3IDoRd5YzzS31hRVfe/+xJEMI0\\nIcvRqTOOjPqciILUE1RhRKfpaUGa4ogdtAGNzckqE8kWPL49UnODbZCBy7Po48l6\\nMXjomSWMXQKBgQCaA7gKMKjpJvlZ8QUyhb09DjNefzR1kibzjV2WU//rRjNl2UlH\\npJAoO7dr97k86t/BIvx9TOe7UhZ3pBc5hU9PeAs4qkok5yJC0mJ2SEAfvG5NI8jN\\nTwdEJvqE8IILgVNIaNxq5lGcUoEIrvkA/ow4BUfmF0ub5+479+LUgKiTLQKBgGQc\\nyFQr8JPxkP7/hOU7cdntfkb2hKw62cjmhxt6vsu/scnJwXxPK/liqMMnk1oQ9pAx\\njtsRDBh/5uvOVX0GsxZ6z6HDxB013LPj4vm4j7rnLNeZLPZTjxmFey45XXn8Tr93\\nVbS2vdhy2Pkp11ZlvD3VRiNXDIC2SrP3CZqLqFydAoGBAOdQXXy8OK4rIH1E7eYY\\nS8nbTst3sm4FT2la5VRhBa9dkopO7IZaSjn08gkBt+8QNzePPY8geoNdrZi2NfPa\\nI4BWBfN+7TSzQBaNyyZAxAabwAyXh/ni4B4tV0zHYBR88iKBeYrRBjZjaW0UuwMc\\nusnyJ3lXgchixKH1fwxt3Vz+\\n"))
                .setDatabaseUrl("https://careertrackerv2.firebaseio.com/")
                .build();

        try {
            FirebaseApp.getInstance();
        }
        catch (Exception error){
            Log.info("doesn't exist...");
        }

        try {
            FirebaseApp.initializeApp(options);
        }
        catch(Exception error){
            Log.info("already exists...");
        }

        // As an admin, the app has access to read and write all data, regardless of Security Rules
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference("todoItems");

        // This fires when the servlet first runs, returning all the existing values
        // only runs once, until the servlet starts up again.
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                Log.info("new value: "+ document);

                String todoText = "Don't forget to...\n\n";

                Iterator<DataSnapshot> children = dataSnapshot.getChildren().iterator();

                while(children.hasNext()){
                    DataSnapshot childSnapshot = (DataSnapshot) children.next();
                    todoText = todoText + " * " + childSnapshot.getValue().toString() + "\n";
                }

                // Now send the email

                // Note: When an application running in the development server calls the Mail
                // service to send an email message, the message is printed to the log.
                // The Java development server does not send the email message.

                // You can test the email without waiting for the cron job to run by
                // loading http://[FIREBASE_PROJECT_ID].appspot.com/send-email in your browser.

                Properties props = new Properties();
                Session session = Session.getDefaultInstance(props, null);
                try {
                    Message msg = new MimeMessage(session);
                    //Make sure you substitute your project-id in the email From field
                    msg.setFrom(new InternetAddress("reminder@careertrackerv2.appspotmail.com",
                            "Todo Reminder"));
                    msg.addRecipient(Message.RecipientType.TO,
                            new InternetAddress("jianmei2017@gmail.com", "Recipient"));
                    msg.setSubject("Things to do today");
                    msg.setText(todoText);
                    Transport.send(msg);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    Log.warning(e.getMessage());
                }

                // Note: in a production application you should replace the hard-coded email address
                // above with code that populates msg.addRecipient with the app user's email address.
            }

            @Override
            public void onCancelled(DatabaseError error){
                System.out.println("Error: "+error);
            }
        });
    }
}
