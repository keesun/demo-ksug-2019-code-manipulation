package me.whiteship.rabbit;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.pool.TypePool;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
public class RabbitAnnotationProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Rabbit.class.getName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Rabbit.class);
        for (Element element : elements) {
            if (element.getSimpleName().contentEquals("Box")) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing a Box with Rabbit annotation.");

                TypeElement typeElement = (TypeElement)element;
                ClassName className = ClassName.get(typeElement);
                try {
                    JavaFile.builder(className.packageName(), createRabbitBoxClass(element))
                            .build()
                            .writeTo(processingEnv.getFiler());
                } catch (IOException e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                }
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Rabbit annotation can not be used for " + element.getSimpleName() +  ".");
            }
        }
        return true;
    }

    private TypeSpec createRabbitBoxClass(Element element) {
        return TypeSpec.classBuilder("RabbitBox")
                .superclass(TypeName.get(element.asType()))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(createOpenMethod(element))
                .build();
    }

    private MethodSpec createOpenMethod(Element element) {
        return MethodSpec.methodBuilder("open")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return $S", element.getAnnotation(Rabbit.class).value())
                .build();
    }
}
