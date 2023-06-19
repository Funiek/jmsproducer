/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package pl.ds360.jmsproducer;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Topic;

/**
 *
 * @author Krzysiek
 */
@Named(value = "senderBean")
@RequestScoped
public class SenderBean {
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    
    @Resource(mappedName = "jms/MyTopic")
    private Topic myTopic;
    
    private String messageText;

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    /**
     * Creates a new instance of SenderBean
     */
    public SenderBean() {
    }
    
    public void sendJMSMessageToMyQueue() {
         try {
            String text = "Message from producer: " + messageText;
            System.out.println(text);
            context.createProducer().send(myTopic, text);
 
            FacesMessage facesMessage = new FacesMessage("Sent message: " + text);
            facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } catch (JMSRuntimeException t) {
             System.out.println(t.toString());
        }
    }
}
