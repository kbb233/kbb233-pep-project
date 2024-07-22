package Service;

import java.util.List;


import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessage(){
        return messageDAO.getAllMessage();
    }

    public Message creatMessage(Message msg){
        //the message_text is not blank, is not over 255 characters, and posted_by refers to a real, existing user
        if(!messageDAO.findUserID(msg.getPosted_by())){
            return null;
        }
        if(msg.getMessage_text().isBlank() || msg.getMessage_text().length()>255){
            return null;
        }
        return messageDAO.createMessage(msg);
    }

    public Message getMessageByMID(int id){
        return messageDAO.getMessageByID(id);
    }

    public Message deleteMessageByID(int id){
        Message msg = messageDAO.getMessageByID(id);
        if(messageDAO.deleteMessageByID(id)){return msg;}
        return null;
    }

    public Message updateMessageByID(int mid,String mtext){
        Message msg = messageDAO.getMessageByID(mid);
        if(msg == null){
            return null;
        }
        if(mtext.isBlank() || mtext.length()>255){
            return null;
        }
        return messageDAO.updateMessageByID(mid,mtext);
    }

    public List<Message> getAllMessageByUserId(int uid){
        return messageDAO.getAllMessageByUserId(uid);
    }
    
}
