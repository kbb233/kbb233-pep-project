package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByMIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByMIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageByMIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessageByUIDHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(context.body(), Account.class);
        Account registerAcc = accountService.register(acc);
        if(registerAcc!= null){
            context.json(mapper.writeValueAsString(registerAcc));
        }
        else{
            context.status(400);
        }
        
    }

    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(context.body(), Account.class);
        Account loginrAcc = accountService.login(acc.getUsername(),acc.getPassword());
        if(loginrAcc!= null){
            context.json(loginrAcc);
        }
        else{
            context.status(401);
        }
    }

    private void createMessageHandler(Context context)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(context.body(), Message.class);
        Message createMsg = messageService.creatMessage(msg);
        if(createMsg!= null){
            context.json(mapper.writeValueAsString(createMsg));
        }
        else{
            context.status(400);
        }

    }

    private void getAllMessageHandler(Context context)throws JsonProcessingException{
        List<Message> msgs = messageService.getAllMessage();
        context.json(msgs);

    }

    private void getMessageByMIDHandler(Context context)throws JsonProcessingException{
        int mid = Integer.parseInt(context.pathParam("message_id"));
        Message readMsg = messageService.getMessageByMID(mid);
        if(readMsg!= null){
            context.json(readMsg);
        }
        else{
            context.status(200);
        }

    }

    private void deleteMessageByMIDHandler(Context context)throws JsonProcessingException{
        int mid = Integer.parseInt(context.pathParam("message_id"));
        Message readMsg = messageService.deleteMessageByID(mid);
        if(readMsg!= null){
            context.json(readMsg);
        }
        else{
            context.status(200);
        }

    }

    private void updateMessageByMIDHandler(Context context)throws JsonProcessingException{
        int mid = Integer.parseInt(context.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        String mtext = mapper.readTree(context.body()).get("message_text").asText();
        Message readMsg = messageService.updateMessageByID(mid,mtext);
        if(readMsg!= null){
            context.json(readMsg);
        }
        else{
            context.status(400);
        }

    }

    private void getAllMessageByUIDHandler(Context context)throws JsonProcessingException{
        int uid = Integer.parseInt(context.pathParam("account_id"));
        List<Message> msgs = messageService.getAllMessageByUserId(uid);
        context.json(msgs);
    }


}