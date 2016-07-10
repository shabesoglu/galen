/*******************************************************************************
* Copyright 2016 Ivan Shubin http://galenframework.com
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*   http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
******************************************************************************/
package com.galenframework.suite.reader;

import com.galenframework.parser.VarsContext;

public class SetNode extends Node<Void> {

    public SetNode(String text, Line line) {
        super(text, line);
    }

    @Override
    public Void build(VarsContext context) {
        String line = context.process(getArguments());
        int indexOfFirstSpace = getArguments().indexOf(' ');
        
        if (indexOfFirstSpace > 0) {
            String name = line.substring(0, indexOfFirstSpace);
            String value = line.substring(indexOfFirstSpace).trim();
            context.putValue(name, value);
        }
        else {
            context.putValue(line, "");
        }
        
        for (Node<?> childNode : getChildNodes()) {
            if (childNode instanceof SetNode) {
                SetNode setNode = (SetNode)childNode;
                setNode.build(context);
            }
        }
        
        return null;
    }

    @Override
    public Node<?> processNewNode(String text, Line line) {
        add(new SetNode(text.trim(), line));
        return this;
    }

}
