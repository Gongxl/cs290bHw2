/*
 * The MIT License
 *
 * Copyright 2015 peter.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package clients;

import api.Job;
import api.JobRunner;
import java.awt.BorderLayout;
import java.awt.Container;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author Peter Cappello
 * @param <T> return type the Task that this OldClient executes.
 */
abstract public class Client<T> extends JFrame
{    
    final private long startTime = System.nanoTime();
    final private JobRunner jobRunner;
    final private Job<T> job;
    
    public Client( final String title, Job<T> job ) throws RemoteException
    {     
        System.setSecurityManager( new SecurityManager() );
        setTitle( title );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.job = job;
        jobRunner = new JobRunner( job );
    }
    
    public Client( final String title, Job<T> job, String spaceDomainName ) 
            throws RemoteException, NotBoundException, MalformedURLException
    {     
        System.setSecurityManager( new SecurityManager() );
        setTitle( title );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.job = job;
        jobRunner = new JobRunner( job, spaceDomainName );
    }
    
    /**
     *
     * @throws RemoteException
     */
//    public void run( final Job<T> job ) throws RemoteException
    public void run() throws RemoteException
    {
        jobRunner.run();
        add( getLabel( job.getValue() ) );
        Logger.getLogger(this.getClass().getCanonicalName() ).log(Level.INFO, "Client time: {0} ms.", ( System.nanoTime() - startTime) / 1000000 );
    }
    
    private void add( final JLabel jLabel )
    {
        final Container container = getContentPane();
        container.setLayout( new BorderLayout() );
        container.add( new JScrollPane( jLabel ), BorderLayout.CENTER );
        pack();
        setVisible( true );
    }
    
    abstract JLabel getLabel( final T returnValue );
}
 