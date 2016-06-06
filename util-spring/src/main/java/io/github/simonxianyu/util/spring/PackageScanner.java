package io.github.simonxianyu.util.spring;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Use spring package search related class to collect Class annotation information.
 * Because spring using ASM class reader, so detail class information is not loaded.
 * Created by simon on 16/5/11.
 */
public class PackageScanner {
  protected PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
  private MetadataReaderFactory metadataReaderFactory =
      new CachingMetadataReaderFactory(this.resourcePatternResolver);

  public Set<MethodMetadata> search(Class<?> annotationClass,MethodCallback callback, String... packages) throws IOException {
    Set<MethodMetadata> methodSet = new HashSet<>();
    if (null == packages || packages.length<1) {
      throw new IllegalArgumentException("no package specified");
    }
    for(String p : packages) {
      String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
          ClassUtils.convertClassNameToResourcePath(p)
          + "/" + "**/*.class";
      Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
      if (null != resources) {
        for(Resource resource : resources) {
          MetadataReader reader = metadataReaderFactory.getMetadataReader(resource);
          ClassMetadata classMetadata = reader.getClassMetadata();
          AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
          if (!isCandidateComponent(annotationMetadata)) {
            continue;
          }

          if (annotationMetadata.hasAnnotatedMethods(annotationClass.getCanonicalName())) {
//            System.out.println(classMetadata.getClassName());
            Set<MethodMetadata> methods = annotationMetadata.getAnnotatedMethods(annotationClass.getCanonicalName());
            methodSet.addAll(methods);
            if (null != callback && methods.size()>0  ) {
              for(MethodMetadata m : methods) {
                //              System.out.println("  "+m.getMethodName());
                callback.process(classMetadata, annotationMetadata, m);
              }
            }
          }
        }
      }
    }
    return methodSet;
  }

  /**
   * Use this callback to handle found method.
   */
  interface MethodCallback {
    void process(ClassMetadata classMetadata, AnnotationMetadata annotationMetadata, MethodMetadata methodMetadata);
  }

  protected boolean isCandidateComponent(AnnotationMetadata metadata) {
    return metadata.isConcrete() && metadata.isIndependent();
  }

}
