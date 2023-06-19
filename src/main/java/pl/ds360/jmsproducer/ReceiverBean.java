/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package pl.ds360.jmsproducer;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Topic;

/**
 *
 * @author Krzysiek
 */
@Named(value = "receiverBean")
@RequestScoped
public class ReceiverBean {

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
    @Resource(lookup = "jms/MyTopic")
    private Topic topic;


    /**
     * Creates a new instance of ReceiverBean
     */
    public ReceiverBean() {
    }
    
    public void getMessage() {
        try {
            JMSConsumer receiver = context.createConsumer(topic);
            String text = receiver.receiveBody(String.class, 5000);

            if (text != null) {
                System.out.println("OOOOO DZIALA");
                FacesMessage facesMessage = new FacesMessage("Reading message: " + text);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            } else {
                System.out.println("AAAAA NIE DZIALA");
                FacesMessage facesMessage = new FacesMessage("No message received after 1 second");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }
        } catch (JMSRuntimeException t) {
        
                    System.out.println(t.toString());
        }
    }
}
