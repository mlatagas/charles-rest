/**
 * Copyright (c) 2016-2017, Mihai Emil Andronache
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1)Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  2)Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  3)Neither the name of charles-rest nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.amihaiemil.charles.github;

import java.io.IOException;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link IndexSiteKn}
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id$
 * @since 1.0.1
 */
public final class IndexSiteKnTestCase {

    /**
     * IndexSiteKn can handle an 'indexsite' command.
     * @throws Exception If something goes wrong.
     */
    @Test
    public void handlesIndexSiteCommand() throws Exception {
        final Command com = Mockito.mock(Command.class);
        Mockito.when(com.type()).thenReturn("indexsite");
        Mockito.when(com.authorLogin()).thenReturn("amihaiemil");
        Mockito.when(com.language()).thenReturn(new English());

        final LogsLocation logs = Mockito.mock(LogsLocation.class);
        Mockito.when(logs.address()).thenReturn("/path/to/logs");
        
        final Knowledge indexsite = new IndexSiteKn(
            logs,
            new Knowledge() {
                @Override
                public Steps handle(final Command com) throws IOException {
                    throw new IllegalStateException(
                        "'indexsite' command was misunderstood!"
                    );
                }
            }
        );

        Steps steps = indexsite.handle(com);
        MatcherAssert.assertThat(steps, Matchers.notNullValue());
    }
    
    /**
     * IndexSiteKn can handle a command which is not 'indexsite'.
     * @throws Exception If something goes wrong.
     */
    @Test
    public void handlesNotIndexSiteCommand() throws Exception {
        final Command com = Mockito.mock(Command.class);
        Mockito.when(com.type()).thenReturn("hello");
        
        final Knowledge indexsite = new IndexSiteKn(
            Mockito.mock(LogsLocation.class),
            new Knowledge() {
                @Override
                public Steps handle(final Command com) throws IOException {
                    MatcherAssert.assertThat(
                        com.type(),
                        Matchers.equalTo("hello")
                    );
                    return null;
                }
            }
        );
        indexsite.handle(com);
    }
}