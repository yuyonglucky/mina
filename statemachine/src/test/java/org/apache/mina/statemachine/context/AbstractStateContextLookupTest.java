/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.mina.statemachine.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.statemachine.context.AbstractStateContextLookup;
import org.apache.mina.statemachine.context.DefaultStateContextFactory;
import org.apache.mina.statemachine.context.StateContext;

import junit.framework.TestCase;

/**
 * Tests {@link AbstractStateContextLookup}.
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 */
public class AbstractStateContextLookupTest extends TestCase {

    public void testLookup() throws Exception {
        Map map = new HashMap();
        AbstractStateContextLookup lookup = new AbstractStateContextLookup(
                                             new DefaultStateContextFactory()) {
            protected boolean supports(Class c) {
                return Map.class.isAssignableFrom(c);
            }
            protected StateContext lookup(Object eventArg) {
                Map map = (Map) eventArg;
                return (StateContext) map.get("context");
            }
            protected void store(Object eventArg, StateContext context) {
                Map map = (Map) eventArg;
                map.put("context", context);
            }
        };
        Object[] args1 = new Object[] {new Object(), map, new Object()};
        Object[] args2 = new Object[] {map, new Object()};
        StateContext sc = lookup.lookup(args1);
        assertSame(map.get("context"), sc);
        assertSame(map.get("context"), lookup.lookup(args1));
        assertSame(map.get("context"), lookup.lookup(args2));
    }
    
}