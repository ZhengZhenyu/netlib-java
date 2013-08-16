package com.github.fommil.netlib.generator;

import com.google.common.collect.Lists;
import org.stringtemplate.v4.STGroupFile;

import java.lang.reflect.Method;
import java.util.List;

import static java.lang.String.format;

public abstract class AbstractJavaGenerator extends AbstractNetlibGenerator {

    protected final STGroupFile jTemplates = new STGroupFile("com/github/fommil/netlib/generator/netlib-java.stg", '$', '$');

    protected String getTargetPackage() {
        return outputName.replace("/", ".").substring(0, outputName.lastIndexOf("/"));
    }

    protected String getTargetClassName() {
        return outputName.replace(".java", "").substring(outputName.lastIndexOf("/") + 1);
    }

    /**
     * @param method
     * @return parameters to pass to the F2J implementation.
     */
    protected List<String> getF2jJavaParameters(Method method, final boolean offsets) {
        final List<String> args = Lists.newArrayList();
        iterateRelevantParameters(method, offsets, new ParameterCallback() {
            @Override
            public void process(int i, Class<?> param, String name) {
                args.add(name);
                if (param.isArray() && !offsets) args.add("0");
            }
        });
        return args;
    }

    /**
     * @return a Javadoc summary text of the plugin parameters.
     */
    protected String getGenerationSummaryJavadocs() {
        return format(
                "Generated by {@code %s} from {@code %s} in {@code %s}.",
                getClass().getSimpleName(), scan, project.getArtifactMap().get(input)
        );
    }

}
