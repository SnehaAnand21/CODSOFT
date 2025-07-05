import numpy as np
from tensorflow.keras.applications.inception_v3 import InceptionV3, preprocess_input
from tensorflow.keras.preprocessing import image
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.sequence import pad_sequences
import pickle

# Load pre-trained CNN for image feature extraction
inception_model = InceptionV3(weights='imagenet', include_top=False, pooling='avg')

# Load your trained tokenizer and caption generator model
tokenizer = pickle.load(open("tokenizer.pkl", "rb"))
caption_model = load_model("caption_generator.h5")

def extract_features(img_path):
    """Extract feature vector from image using InceptionV3."""
    img = image.load_img(img_path, target_size=(299, 299))
    x = image.img_to_array(img)
    x = preprocess_input(np.expand_dims(x, axis=0))
    features = inception_model.predict(x)
    return features

def generate_caption(features, max_length=34):
    """Generate caption for image features using LSTM model and tokenizer."""
    in_text = 'startseq'
    for _ in range(max_length):
        sequence = tokenizer.texts_to_sequences([in_text])[0]
        sequence = pad_sequences([sequence], maxlen=max_length)
        yhat = caption_model.predict([features, sequence], verbose=0)
        yhat = np.argmax(yhat)
        word = tokenizer.index_word.get(yhat)
        if word is None:
            break
        in_text += ' ' + word
        if word == 'endseq':
            break
    caption = in_text.replace('startseq', '').replace('endseq', '').strip()
    return caption

if __name__ == "__main__":
    img_path = "example.jpg"  # Replace with your image path
    features = extract_features(img_path)
    caption = generate_caption(features)
    print("Image Caption:", caption)

